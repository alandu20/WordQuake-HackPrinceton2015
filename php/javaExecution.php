<?php 
$input = $_POST['data'];

// Replace quotations with escape keys
$input = str_replace("\"", "\\\"", $input);
#echo $input;
$output = shell_exec('cd java; java NounReplacer synsets.txt hypernyms.txt "'.$input.'"'); 
#echo nl2br($output);
echo $output;?>