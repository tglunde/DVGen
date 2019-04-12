--DV_RAW.VERTRAG_SCHADEN_LINK -
INSERT INTO DV_RAW.Vertrag_Schaden_Link (VERTRAG_SCHADEN_LINK_HK,SATZHERKUNFT,LADEZEITPUNKT,LADEBATCH_ID,SCHADEN_HUB_HK,VERTRAG_HUB_HK)
WITH
	quelle_1 as (
		SELECT MAX(LADEZEITPUNKT) AS LADEZEITPUNKT, MIN(SATZHERKUNFT) AS SATZHERKUNFT
		   ,VSNR AS BK1,SDNR AS BK2,VSNR AS BK3		FROM STG_SD.SD_JOURNAL_ALLGEMEIN WHERE VSNR IS NOT NULL AND SDNR IS NOT NULL AND VSNR IS NOT NULL  GROUP BY VSNR,SDNR,VSNR	),
	link_quelle as (
        select link.* from (
            select  DWH_TOOL.GET_MD5( CAST( DWH_TOOL.GET_BKSTRING_FROM(BK1) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_BKSTRING_FROM(BK2) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_BKSTRING_FROM(BK3) AS VARGRAPHIC(16000))) AS LHK
                    , LADEZEITPUNKT
                    , SATZHERKUNFT
					, BK1, BK2, BK3
                    , row_number() over (partition by BK1, BK2, BK3 order by ladezeitpunkt,satzherkunft) as row_nr
            from (
				select LADEZEITPUNKT, SATZHERKUNFT, BK1, BK2, BK3				from quelle_1

            )
        ) link where row_nr=1
	),
	link_ziel as (
		select VERTRAG_SCHADEN_LINK_HK AS LHK from DV_RAW.Vertrag_Schaden_Link
	)
select
    link_quelle.LHK, link_quelle.SATZHERKUNFT, link_quelle.LADEZEITPUNKT, -1
	, DWH_TOOL.GET_MD5( CAST( DWH_TOOL.GET_BKSTRING_FROM(link_quelle.BK1) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_BKSTRING_FROM(link_quelle.BK2) AS VARGRAPHIC(16000))) AS HK1
	, DWH_TOOL.GET_MD5( CAST( DWH_TOOL.GET_BKSTRING_FROM(link_quelle.BK3) AS VARGRAPHIC(16000))) AS HK2
	from link_quelle
    left join link_ziel on link_ziel.LHK=link_quelle.LHK
where link_ziel.LHK is null
