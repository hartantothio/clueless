<?php

if (isset($_POST['submit'])) {
    $id = md5($_POST['server']);
    header('Location: /pregame.php?id=' . $id);
}

?>
<?php include('header.php'); ?>

<h1>Clueless</h1>
<h2>By Team Awareness</h2>
<div id="container">
    <div id="page-create">
        <form action="" method="post">
            <div class="control-group">
                <label>Play Style</label>
                <div class="control">
                    <select name="style">
                        <option value="">&laquo; Style &raquo;</option>
                        <option value="casual">Casual</option>
                        <option value="serious">Serious</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label>Server Name</label>
                <div class="control">
                    <input type="text" name="server" value="" placeholder="Server Name" />
                </div>
            </div>
            <div class="control-group">
                <label>Password (optional)</label>
                <div class="control">
                    <input type="password" name="password" value="" />
                </div>
            </div>
            <br />
            <ul class="menu">
                <li><a href="/">Main Menu</a></li>
                <li><input type="submit" name="submit" value="Next" /></li>
            </ul>
        </form>
    </div>
</div>

</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/js/jquery-1.10.2.min.js"><\/script>')</script>
<script src="/js/jquery.fancybox.min.js"></script>
<script src="/js/plugin.js"></script>
</body>
</html>





