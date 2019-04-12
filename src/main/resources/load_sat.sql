--DV_RAW.SCHADEN_COC_SAT -
INSERT INTO DV_RAW.Schaden_COC_Sat (SCHADEN_HUB_HK,Satzherkunft,Ladezeitpunkt,Ladebatch_ID,Attribut_HK,PLZ,KUND_NR)
with
    quelle as (
        select stage.* from (
            select LADEZEITPUNKT, SATZHERKUNFT
            , VSNR AS BK1,SDNR AS BK2
            , PLZ AS ATT1,KUND_NR AS ATT2
            , row_number() over (partition by VSNR,SDNR order by LADEZEITPUNKT desc, SATZHERKUNFT) as row_nr
            from STG_SD.SD_JOURNAL_ALLGEMEIN where VSNR IS NOT NULL AND SDNR IS NOT NULL
        ) stage WHERE stage.row_nr=1
    ),
    sat_quelle as (
        select DWH_TOOL.GET_MD5( CAST( DWH_TOOL.GET_BKSTRING_FROM(BK1) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_BKSTRING_FROM(BK2) AS VARGRAPHIC(16000))) AS HK
            , ladezeitpunkt
            , satzherkunft
            , DWH_TOOL.GET_MD5( CAST( DWH_TOOL.GET_BKSTRING_FROM(BK1) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_BKSTRING_FROM(BK2) || DWH_TOOL.GET_SEP() || RTRIM(DWH_TOOL.GET_STRING_FROM(ATT1) || DWH_TOOL.GET_SEP() || DWH_TOOL.GET_STRING_FROM(ATT2), DWH_TOOL.GET_NULL() || DWH_TOOL.GET_SEP() ) AS VARGRAPHIC(16000))) AS ATTRIBUT_HK
            , ATT1, ATT2
        from quelle
    ),
    sat_ziel as (
        select ziel.* from (
        select sat.SCHADEN_HUB_HK AS HK, sat.LADEZEITPUNKT, sat.ATTRIBUT_HK ,
            row_number() over (partition by sat.SCHADEN_HUB_HK order by sat.LADEZEITPUNKT desc) AS row_nr
        from DV_RAW.Schaden_COC_Sat sat
            --join sat_quelle on sat.SCHADEN_HUB_HK=sat_quelle.HK
        ) ziel where row_nr=1
    )
select sat_quelle.HK, sat_quelle.satzherkunft, sat_quelle.ladezeitpunkt, -1, sat_quelle.ATTRIBUT_HK, sat_quelle.ATT1, sat_quelle.ATT2
    from sat_quelle left join sat_ziel on sat_quelle.HK=sat_ziel.HK
where
    sat_ziel.HK is null
or (sat_ziel.HK is not null AND sat_quelle.ATTRIBUT_HK!=sat_ziel.ATTRIBUT_HK AND sat_quelle.LADEZEITPUNKT>sat_ziel.LADEZEITPUNKT)