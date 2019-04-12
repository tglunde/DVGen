--DV_RAW.SCHADEN_HUB -
INSERT INTO DV_RAW.Schaden_Hub (SCHADEN_HUB_HK,SATZHERKUNFT,LADEZEITPUNKT,LADEBATCH_ID,VSNR,SDNR)
WITH
	quelle_1 as (
		SELECT MAX(LADEZEITPUNKT) AS LADEZEITPUNKT, MIN(SATZHERKUNFT) AS SATZHERKUNFT
			, VSNR AS BK1,SDNR AS BK2		FROM STG_SD.SD_JOURNAL_ALLGEMEIN WHERE VSNR IS NOT NULL AND SDNR IS NOT NULL  GROUP BY VSNR,SDNR
	),
	hub_quelle as (
        select hub.* from (
            select   LADEZEITPUNKT
                    , SATZHERKUNFT
                    , BK1, BK2
                    , row_number() over (partition by BK1, BK2 order by ladezeitpunkt,satzherkunft) as row_nr
            from (
				select LADEZEITPUNKT, SATZHERKUNFT, BK1, BK2				from quelle_1
            )
        ) hub where row_nr=1
	),
	hub_ziel as (
		select VSNR AS BK1,SDNR AS BK2 from DV_RAW.Schaden_Hub
	)
select
    DWH_TOOL.GET_MD5( CAST( DWH_TOOL.GET_BKSTRING_FROM(hub_quelle.BK1) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_BKSTRING_FROM(hub_quelle.BK2) AS VARGRAPHIC(16000))) AS HK, hub_quelle.SATZHERKUNFT, hub_quelle.LADEZEITPUNKT, -1, hub_quelle.BK1, hub_quelle.BK2
from hub_quelle
    left join hub_ziel on hub_ziel.BK1=hub_quelle.BK1 AND hub_ziel.BK2=hub_quelle.BK2
where hub_ziel.BK1 IS NULL AND hub_ziel.BK2 IS NULL
