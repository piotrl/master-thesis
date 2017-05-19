#!/usr/bin/env bash

#
# Prepare dump  and upload a new one based on the provided database dump files location
#
# Usage
# db-dump.sh ${database_name} ${database_user}

DATABASE=$1
USERNAME=$2

DATE=`date +"%Y-%m-%d_%H-%M"`
FILENAME="${DATABASE}_${DATE}.sql"
BACKUP_FILE_PATH="${FILENAME}.gz"


# Connections
echo -e "\n\n\n"

echo "Making Plain backup of $DATABASE and GZIP'ing it ..."

if ! pg_dump -U $USERNAME $DATABASE | gzip > $BACKUP_FILE_PATH.in_progress; then
        echo "[!!ERROR!!] Failed to produce plain backup database $DATABASE" 1>&2
else
        mv $BACKUP_FILE_PATH.in_progress $BACKUP_FILE_PATH
        echo -e "\n\n"
        echo -e "FINAL: Database backup completed!\n"
        echo -e "Dump path: $BACKUP_FILE_PATH\n"
fi