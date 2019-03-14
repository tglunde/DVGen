{% extends "create_table.sql" %}
{% block tablename %}{{source_name}}_{{table_name}}_R{% endblock %}
{% block tablegrant -%}
GRANT SELECT ON {{source_name}}_{{table_name}}_R TO PUBLIC;
{% endblock -%}