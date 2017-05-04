#!/bin/sh

exec_func=$1

function dl_src()
{
	# nagios
	curl https://raw.githubusercontent.com/NagiosEnterprises/ndoutils/master/db/mysql.sql > nagios-mysql.sql
	# zabix
	svn export svn://svn.zabbix.com/trunk/create/src/schema.tmpl .
	svn export svn://svn.zabbix.com/trunk/create/bin/gen_schema.pl . && sed -i -e 's/\.\.\/src\///' gen_schema.pl
	perl gen_schema.pl mysql > zabix-mysql.sql
	rm *.tmpl *.pl
}


function db_setup()
{
	# create database
	dbhost=127.0.0.1
	mysql -h $dbhost -u root -e "drop database if exists nagios; create database nagios;"
	mysql -h $dbhost -u root -e "drop database if exists zabix; create database zabix;"
	mysql -h $dbhost -u root -D nagios -e "$(cat nagios-mysql.sql)" 
	mysql -h $dbhost -u root -D zabix -e "$(cat zabix-mysql.sql)" 
}


if [[ "${exec_func}" =~ (dl_src|db_setup) ]]; then
	$exec_func
else
	cat <<EOF
$0 [dl_src] [db_setup]
EOF
fi

