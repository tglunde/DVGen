{% extends "create_table.sql" %}

{% block tablename %}{{source_name}}_{{table_name}}_HA{% endblock %}

{% block technicalfield %}
    {{table_name}}_HK CHAR(16) FOR BIT DATA NOT NULL,
    LDTS TIMESTAMP NOT NULL,
    RSRC VARCHAR(20) NOT NULL,
    {{table_name}}_HF CHAR(16) FOR BIT DATA NOT NULL,
{% endblock %}
{% block tablegrant %}
GRANT SELECT ON {{source_name}}_{{table_name}}_HA TO PUBLIC;
{% endblock %}