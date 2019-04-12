{% extends "create_table.sql" %}
{% block tablename %}{{sattable(subject_area,cbc, context)}}{% endblock %}
{% block technicalfield %}
    {{hubname(subject_area,cbc)}}_SK BIGINT NOT NULL,
    {{hubname(subject_area,cbc)}}_LDTS TIMESTAMP NOT NULL,
    {{hubname(subject_area,cbc)}}_RSRC VARCHAR(50) NOT NULL{% if context.attributes is not empty %},
    {% endif %}
{% endblock %}
{% block businessfield %}
    {% for attribute in context.attributes %}
    {% if attribute.attributeprefix is not empty%}{{attribute.attributeprefix | upper | replace({' ':"_"})}}{%else%}{{hubname(subject_area,cbc)}}{%endif%}_{{attribute.attributename | upper | replace({' ':"_"})}} {{attribute.column.type | upper}}({{attribute.column.size}}){% if not loop.last %},{%endif%}
    {% endfor %}
{% endblock %}
{% block tablegrant %}
GRANT INSERT,DELETE,ALTER ON {{sattable(subject_area,cbc, context)}} TO ETLRLOAD;
{% endblock %}
{% block constraint %}
ALTER TABLE {{sattable(subject_area,cbc, context)}} ADD CONSTRAINT PK_{{sattable(subject_area,cbc, context)}} PRIMARY KEY({{hubname(subject_area,cbc)}}_SK,{{hubname(subject_area,cbc)}}_LDTS);
ALTER TABLE {{sattable(subject_area,cbc, context)}} ADD CONSTRAINT FK_{{sattable(subject_area,cbc, context)}} FOREIGN KEY ({{hubname(subject_area,cbc)}}_SK) REFERENCES {{hubtable(subject_area,cbc)}} NOT ENFORCED;
{% endblock %}
