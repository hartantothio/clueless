<?php

if (!isset($_POST['language'])) {
    $lang = 'english';
} else if ($_POST['language'] == 'English' || $_POST['language'] == 'Bahasa Inggris') {
    $lang = 'english';
} else {
    $lang = 'indonesian';
}

if ($lang == 'english') {
    $create = 'Create Game';
    $join = 'Join Game';
    $how = 'How To Play';
    $language = 'Language';
    $list = array('English', 'Indonesian');
    $sound = 'Sound';
    $back = 'Main Menu';
} else {
    $create = 'Bikin Game';
    $join = 'Gabung Game';
    $how = 'Instruksi Main';
    $language = 'Bahasa';
    $list = array('Bahasa Indonesia', 'Bahasa Inggris');
    $sound = 'Suara';
    $back = 'Menu Utama';
}

?>
<?php include('header.php'); ?>

<h1>Clueless</h1>
<h2>By Team Awareness</h2>
<div id="container">
    <div id="page-main">
        <ul class="menu">
            <li><a href="/create.php"><?php echo $create; ?></a></li>
            <li><a href="/join.php"><?php echo $join; ?></a></li>
            <li><a href="#settings" data-toggle="modal">Settings</a></li>
            <li><a href="/clueless.pdf" target="_blank"><?php echo $how; ?></a></li>
        </ul>
    </div>
</div>

<div class="modal"><div id="settings" class="box">
    <h2>Settings</h2>
    <form action="" method="post">
        <div class="control-group">
            <label><?php echo $language; ?>: </label>
            <div class="control">
                <select name="language">
                    <?php foreach ($list as $language) { ?>
                        <option value="<?php echo $language; ?>"><?php echo $language; ?></option>
                    <?php } ?>
               </select>
            </div>
        </div>
        <div class="control-group">
            <label><?php echo $sound; ?>: </label>
            <div class="control">
                <select name="sound">
                    <option value="1">On</option>
                    <option value="0">Off</option>
               </select>
            </div>
        </div>
        <div class="form-actions">
            <ul class="menu">
                <li><a href="#" class="modal-close"><?php echo $back; ?></a></li>
                <li><input type="submit" value="Save Changes" /></li>
            </ul>
        </div>
    </form>
</div></div>

</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/js/jquery-1.10.2.min.js"><\/script>')</script>
<script src="/js/jquery.fancybox.min.js"></script>
<script src="/js/plugin.js"></script>
</body>
</html>





