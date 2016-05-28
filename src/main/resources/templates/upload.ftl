<!DOCTYPE html>

<html lang="en">

<body>
<h1>Log Upload</h1>
<#if uploaded??>
    <p style="color: green;">File uploaded successfully!</p>
</#if>
<form method="post" enctype="multipart/form-data" action="upload">
    Upload File: <input type="file" name="file">
    <br/><br/><input type="submit" value="Upload">
</form>
<br/><br/>
<a href="/log/report">>>> Show report <<<</a>
</body>

</html>