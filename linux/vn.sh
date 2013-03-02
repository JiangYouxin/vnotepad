# /bin/sh

FILENAME="$HOME/vnotepad/notepad.txt"
FILENAME_ORIG="$FILENAME.orig"
FILENAME_SERVER="$FILENAME.server"

upload () {
    echo "in upload()"
}

download () {
    echo "in download()"
    cp -r $FILENAME_ORIG $FILENAME_SERVER
}

update () {
    download
    diff -q $FILENAME_ORIG $FILENAME_SERVER
    if [ $? -ne 0 ]; then
        git merge-file $FILENAME $FILENAME_ORIG $FILENAME_SERVER
        mv -r $FILENAME_SERVER $FILENAME_ORIG
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
diff -q $FILENAME $FILENAME_ORIG
if [ $? -ne 0 ]; then
    commit
fi
