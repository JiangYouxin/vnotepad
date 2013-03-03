<?php
$kv = new SaeKV();
$ret = $kv->init();
if ($ret == true) {
	if ($_GET["action"] == "download") {
		$content = $kv->get("vnotepad");
		print $content;
	} else if ($_GET["action"] == "upload"){
		$content = $_POST["vnotepad"];
		$kv->set("vnotepad", $content);
		print "SUCESSS.";
	} else {
		$content = $kv->get("vnotepad");
		$output = htmlentities ($content, ENT_QUOTES, 'UTF-8');
?><html>
<head>
<title>VNOTEPAD</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<form action="vnotepad.php?action=upload" method="post">
<input type="submit" value="Upload"/>
<textarea rows="25" cols="80" name="vnotepad"><?=$output?></textarea>
</form>
</body>
</html>
<?	}
}
?>