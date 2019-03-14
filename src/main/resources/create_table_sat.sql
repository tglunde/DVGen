{% extends "create_table.sql" %}
{% block tablename %}{{source_name}}_{{table_name}}_S{% endblock %}
{% block technicalfield %}
    {{table_name}}_HK CHAR(16) FOR BIT DATA NOT NULL,
    LDTS TIMESTAMP NOT NULL,
    {{table_name}}_HF CHAR(16) FOR BIT DATA NOT NULL,
{% endblock %}