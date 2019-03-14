{% macro columnlist(columns) %}
	{%- for column in columns %}
        {% if column.type == "VARCHAR" %}
           {{column.name}} {{column.type}}({{column.size-}})
        {% elseif column.type == "BIGINT" %}
           {{ column.name }} {{ column.type }}
        {% elseif column.type == "INTEGER" %}
           {{ column.name }} {{ column.type }}
        {% elseif column.type == "SMALLINT" %}
           {{ column.name }} {{ column.type }}
        {% elseif column.type == "TIMESTAMP" %}
           {{ column.name }} {{ column.type }}
        {% elseif column.type == "DATE" %}
           {{ column.name }} {{ column.type }}
        {% elseif column.type == "DECIMAL" %}
           {{ column.name }} DECIMAL(19,6)
        {% else %}
            UNSUPPORTED TYPE {{column.type}}
        {% endif %}
        {% if not loop.last %},{% endif -%}
    {% endfor -%}
{% endmacro %}
CREATE TABLE {% block tablename %}{{table_name}}{% endblock %} (
{% block technicalfield -%}{%- endblock -%}{%- block businessfield -%}{{ columnlist(columns) }}{%- endblock %}
)
IN {{table_space_name}} INDEX IN {{index_space_name}}
 COMPRESS YES;
{% block tablegrant -%}{% endblock -%}
