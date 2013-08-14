<?php

$id = $_GET['id'];
$server = '';
if ($id == md5('test')) {
    $server = 'test';
    $title = ' - test';
} else if ($id == '098f6bcd4621d374cade4e832627b4f6') { // second player join the game
    $server = 'test';
    $title = ' - test';
} else if ($id == md5('test2')) {
    $server = 'test2';
    $title = ' - test2';
}

?>
<?php include('header.php'); ?>

<h1>Clueless</h1>
<h2>By Team Awareness</h2>
<div id="container">
    <div id="page-pregame">
        <h3>&laquo; <?php echo $server; ?> &raquo;</h3>
        <table id="table" border="1" cellpadding="5" cellspacing="0">
            <thead>
                <tr>
                    <th>Player</th>
                    <th>Character</th>
                </tr>
            </thead>
            <tbody>
                <?php if ($id == md5('test')) { ?>
                    <tr>
                        <td>1</td>
                        <td><select id="character" name="character">
                            <option value="Professor Plum">Professor Plum</option>
                            <option value="Colonel Mustard">Colonel Mustard</option>
                            <option value="Mr. Green">Mr. Green</option>
                            <option value="Mrs. Peacock">Mrs. Peacock</option>
                            <option value="Mrs. White">Mrs. White</option>
                            <option value="Miss Scarlet">Miss Scarlet</option>
                            <option value="Random">Random</option>
                        </select></td>
                    </tr>
                <?php } else if ($id == '098f6bcd4621d374cade4e832627b4f6') { ?>
                    <tr>
                        <td>1</td>
                        <td><select id="character" name="character">
                            <option value="Miss Scarlet">Miss Scarlet</option>
                            <option value="Random">Random</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Professor Plum</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Colonel Mustard</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>Mr. Green</td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>Mrs. Peacock</td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>Mrs. White</td>
                    </tr>
                <?php } else if ($id == md5('test2')) { ?>
                    <tr>
                        <td>1</td>
                        <td><select id="character" name="character">
                            <option value="Professor Plum">Professor Plum</option>
                            <option value="Colonel Mustard">Colonel Mustard</option>
                            <option value="Mr. Green">Mr. Green</option>
                            <option value="Mrs. Peacock">Mrs. Peacock</option>
                            <option value="Mrs. White">Mrs. White</option>
                            <option value="Miss Scarlet">Miss Scarlet</option>
                            <option value="Random">Random</option>
                        </select></td>
                    </tr>
                <?php } ?>
            </tbody>
        </table><br />
        <ul class="menu">
            <li><a href="/">Main Menu</a></li>
        </ul>
    </div>
</div>

</body>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/js/jquery-1.10.2.min.js"><\/script>')</script>
<script src="/js/jquery.fancybox.min.js"></script>
<script src="/js/plugin.js"></script>
<script>
<?php
if ($id == md5('test')) {
    ?>

    var $table = $('#table tbody'),
        _zzz = 0;

    $(window).on('focus', function () {
        if (_zzz === 0) {
            $table.append('<tr><td>6</td><td>Miss Scarlet</td></tr>');
            $('.menu').append('<li><a href="/game.php?id=<?php echo $id; ?>">Play!</a></li>');
            _zzz = 1
        }
        return false;
    });

    var players = [
        {
            num: '2',
            name: 'Colonel Mustard',
            ts: 2000
        },
        {
            num: '3',
            name: 'Mr. Green',
            ts: 2100
        },
        {
            num: '4',
            name: 'Mrs. Peacock',
            ts: 5000
        },
        {
            num: '5',
            name: 'Mrs. White',
            ts: 6000
        }
    ];

    for (var i = 0; i < players.length; i++) {
        (function(index) {
            var num  = players[index]['num'],
                name = players[index]['name'],
                ts   = players[index]['ts'];

            setTimeout(function () {
                $table.append('<tr><td>' + num + '</td><td>' + name + '</td></tr>');
                $('#character').find('option[value="' + name + '"]').remove();
            }, ts);
        })(i);
    }
    <?php
} else if ($id == '098f6bcd4621d374cade4e832627b4f6') {
    ?>

    var _zzz = 0;

    $(window).on('focus', function () {
        if (_zzz === 0) {
            window.location = '/game.php?id=098f6bcd4621d374cade4e832627b4f6';
            _zzz = 1;
        }
        return false;
    })
    <?php
}
?>

</script>
</body>
</html>





