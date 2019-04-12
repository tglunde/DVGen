{% extends "create_table.sql" %}
{% block tablename %}{{hubtable(subject_area,cbc)}}{% endblock %}
{% block technicalfield %}
    {{hubname(subject_area,cbc)}}_SK BIGINT NOT NULL,
    {{hubname(subject_area,cbc)}}_LDTS TIMESTAMP NOT NULL,
    {{hubname(subject_area,cbc)}}_RSRC VARCHAR(50) NOT NULL,
{% endblock %}
{% block businessfield %}    {{hubname(subject_area,cbc)}}_BK VARCHAR(254) NOT NULL{%- endblock %}
{% block tablegrant %}
GRANT INSERT,DELETE,ALTER ON {{hubtable(subject_area,cbc)}} TO ETLRLOAD;
{% endblock %}
{% block constraint %}
ALTER TABLE {{hubtable(subject_area,cbc)}} ADD CONSTRAINT PK_{{hubtable(subject_area,cbc)}} PRIMARY KEY({{hubname(subject_area,cbc)}}_SK);
ALTER TABLE {{hubtable(subject_area,cbc)}} ADD CONSTRAINT UC_{{hubtable(subject_area,cbc)}} UNIQUE({{hubname(subject_area,cbc)}}_BK);
{% endblock %}
