# /bin/sh

FILENAME="$HOME/vnotepad/notepad.txt"
FILENAME_ORIG="$FILENAME.orig"
FILENAME_SERVER="$FILENAME.server"

URL="http://better9.sinaapp.com/vnotepad.php?"
DOWNLOAD="action=download"
UPLOAD="action=upload"

upload () {
    CONTENT=`cat $FILENAME`;
    VALUE=`(perl -MURI::Escape -e 'print uri_escape($ARGV[0]);' "$CONTENT")`;
    POST="vnotepad=$VALUE"
    wget "$URL$UPLOAD" -q --post-data=$POST -O /dev/null
    if [ $? -eq 0 ]; then
        cp -r $FILENAME $FILENAME_ORIG
    else
        echo "upload error!"
    fi
}

download () {
    wget -q "$URL$DOWNLOAD" -O $FILENAME_SERVER
}

update () {
    download
    diff -w -q $FILENAME_ORIG $FILENAME_SERVER
    if [ $? -ne 0 ]; then
        git merge-file $FILENAME $FILENAME_ORIG $FILENAME_SERVER
        mv $FILENAME_SERVER $FILENAME_ORIG
        return 1
    else
        return 0
    fi
}

commit () {
    update
    upload
}

touch $FILENAME
touch $FILENAME_ORIG
update 
vim $FILENAME
diff -w -q $FILENAME $FILENAME_ORIG
if [ $? -ne 0 ]; then
    commit
fi
