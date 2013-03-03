<?php
$kv = new SaeKV();
$ret = $kv->init();
if ($ret == true) {
	if ($_GET["action"] == "download") {
		$content = $kv->get("vnotepad");
		print $content;
	} else {
		$content = $_POST["vnotepad"];
		$kv->set("vnotepad", $content);
		print "SUCESSS.";
	}
}
?>