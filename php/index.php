<head>
	<?php 
	include("php/htmlhead.php"); 
	?>
</head>
<body>

<!-- Facebook Initialization -->
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.3";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
<!-- Twitter Initialization -->
<script>
window.twttr=(function(d,s,id){
	var js,fjs=d.getElementsByTagName(s)[0],t=window.twttr||{};
	if(d.getElementById(id))
		return t;
	js=d.createElement(s);
	js.id=id;js.src="https://platform.twitter.com/widgets.js";
	fjs.parentNode.insertBefore(js,fjs);t._e=[];
	t.ready=function(f){
		t._e.push(f);
	};
	return t;
}
(document,"script","twitter-wjs"));
</script>

<!-- End Facebook Initialization -->
<div class="starter-template" id="content" style="border: 1px solid;height:570px">
    <h1>
    	<a href="index.php">WordQuake</a> Text Transformer
    </h1>
    <h4>
    	Enter some text to transform. Try news excerpts, famous quotes, or poetry and see a whole new meaning.
    </h4>

    <!-- The content: left side is text box, right side is java output -->
    <div style="width:50%; float:left; padding: 10px">
	<form action="index.php" method="post" id="usrform">
		<div class="form-group">
			<textarea placeholder="Input text here" name="data" form="usrform" style="height:300px; width:100%; 
			border: 1px solid; border-radius: 10px; margin-bottom:10px"><?php echo $_POST["data"]; ?></textarea>
			<button class="btn btn-primary" type="submit">Transform</button>
		</div>
	</form>
	</div>
    <div style="width:40%; float:left; padding:10px;">
    	<textarea placeholder="Click the Transform button below" name="data" style="height:300px; width:100%;overflow-x: hidden;
    	border: 1px solid; border-radius: 10px; margin-bottom:10px;"><?php include("php/javaExecution.php");?></textarea>
		<div class="fb-share-button" data-href="https://wordquake.me" data-layout="button_count"></div>
		<a class="twitter-share-button" href="https://twitter.com/share?text=I just transformed my words on WordQuake">Tweet</a>
    </div>
    <!-- End content: left side is text box, right side is java output -->
    <div style="clear:both">
<h1>Get our Chrome extension here</h1>

</div>


<br>
<div style="position:relative; width: 100%; text-align:center">
	<!-- Insert footer -->
	<ul style="list-style-type: none; position:relative; left:-50px;">
	<li><a href="about.php"> About WordQuake</a></li>
	<li>Developed at Princeton University, 2015</li>
	</ul>
</div>
</body> 


