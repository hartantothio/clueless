<?php include('header.php'); ?>

<h1>Clueless</h1>
<h2>By Team Awareness</h2>
<div id="container">
    <div id="page-join">
        <form action="" method="post">
            <select name="style">
                <option value="">&laquo; Style &raquo;</option>
                <option value="casual">Casual</option>
                <option value="serious">Serious</option>
            </select>
            <select name="secured">
                <option value="">&laquo; Secured &raquo;</option>
                <option value="0">No</option>
                <option value="1">Yes</option>
            </select>
            <input type="submit" name="search" value="Search" />
        </form><br />
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
                <th>Name</th>
                <th>Player</th>
                <th>Secured</th>
                <th>Style</th>
            </tr>
            <tr>
                <td><a href="/pregame.php?id=098f6bcd4621d374cade4e832627b4f6">&laquo; test &raquo;</a></td>
                <td>(5/6)</td>
                <td>No</td>
                <td>Casual</td>
            </tr>
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
$(function () {
    var _zzz = 0;

    $(window).on('focus', function () {
        if (_zzz === 0) {
            $('table').append('<tr><td><a href="/pregame.php?id=ad0234829205b9033196ba818f7a872b" onclick="return prompt(\'Enter Password\') == \'12345\';">&laquo; test2 &raquo;</a></td><td>(1/6)</td><td>Yes</td><td>Serious</td></tr>');
            _zzz = 1;
        }
        return false;
    });
});
</script>
</body>
</html>





