<?php

$id = $_GET['id'];
$server = '';
if ($id == md5('test')) {
    $server = 'test';
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
if ($server == 'test') {
    ?>
    var players = [
        {
            num: '2',
            name: 'Colonel Mustard',
            ts: 2000
        },
        {
            num: '3',
            name: 'Mr. Green',
            ts: 5000
        },
        {
            num: '4',
            name: 'Mrs. Peacock',
            ts: 10000
        },
        {
            num: '5',
            name: 'Mrs. White',
            ts: 12000
        },
        {
            num: '6',
            name: 'Miss Scarlet',
            ts: 13000
        }
    ];

    var $table = $('#table tbody');
    for (var i = 0; i < players.length; i++) {
        (function(index) {
            var num  = players[index]['num'],
                name = players[index]['name'],
                ts   = players[index]['ts'];

            setTimeout(function () {
                $table.append('<tr><td>' + num + '</td><td>' + name + '</td></tr>');
                $('#character').find('option[value="' + name + '"]').remove();
                if (num == '6') {
                    $('.menu').append('<li><a href="/game.php?id=<?php echo $id; ?>">Play!</a></li>');
                }
            }, ts);
        })(i);
    }
    <?php
}
?>

</script>
</body>
</html>





