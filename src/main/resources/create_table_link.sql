{% extends "create_table.sql" %}
{% block tablename %}{{lnktable(subject_area,nbr)}}{% endblock %}
{% block technicalfield %}
    {{lnkname(subject_area,nbr)}}_LDTS TIMESTAMP NOT NULL,
    {{lnkname(subject_area,nbr)}}_RSRC VARCHAR(50) NOT NULL,
{% endblock %}
{% block businessfield %}
    {{lnkname(subject_area,nbr)}}_{{hubname(subject_area,nbr.cbc)}}_SK BIGINT NOT NULL,
    {% for cbc in nbr.cbcs %}
    {{lnkname(subject_area,nbr)}}_{{hubname(subject_area,cbc)}}_SK BIGINT NOT NULL{% if not loop.last %},{%endif%}
    {% endfor %}
{%- endblock %}
{% block tablegrant %}
GRANT INSERT,DELETE,ALTER ON {{lnktable(subject_area,nbr)}} TO ETLRLOAD;
{% endblock %}
{% block constraint %}
ALTER TABLE {{lnktable(subject_area,nbr)}} ADD CONSTRAINT UC_{{lnktable(subject_area,nbr)}} UNIQUE(
{{lnkname(subject_area,nbr)}}_{{hubname(subject_area,nbr.cbc)}}_SK,
{% for cbc in nbr.cbcs %}
{{lnkname(subject_area,nbr)}}_{{hubname(subject_area,cbc)}}_SK{% if not loop.last %},{%endif%}
{% endfor %}
);
ALTER TABLE {{lnktable(subject_area,nbr)}} ADD CONSTRAINT FK_{{lnkname(subject_area,nbr)}}_KI FOREIGN KEY ({{lnkname(subject_area,nbr)}}_{{hubname(subject_area,nbr.cbc)}}_SK) REFERENCES {{hubtable(subject_area,nbr.cbc)}};
{% for cbc in nbr.cbcs %}
ALTER TABLE {{lnktable(subject_area,nbr)}} ADD CONSTRAINT FK_{{lnkname(subject_area,nbr)}}_H{{loop.index+1}} FOREIGN KEY ({{lnkname(subject_area,nbr)}}_{{hubname(subject_area,cbc)}}_SK) REFERENCES {{hubtable(subject_area,cbc)}};
{% endfor %}
{% endblock %}
