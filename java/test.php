<?php 
$output = shell_exec('java NounReplacer synsets.txt hypernyms.txt wolf.txt'); 
echo $output;
?>

