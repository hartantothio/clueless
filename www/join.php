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
                <td><a href="/pregame.php?id=123">&laquo; Server Name &raquo;</a></td>
                <td>(1/6)</td>
                <td>N</td>
                <td>C</td>
            </tr>
            <tr>
                <td><a href="/pregame.php?id=123">&laquo; Server Name &raquo;</a></td>
                <td>(1/6)</td>
                <td>N</td>
                <td>C</td>
            </tr>
            <tr>
                <td><a href="/pregame.php?id=123">&laquo; Server Name &raquo;</a></td>
                <td>(1/6)</td>
                <td>N</td>
                <td>C</td>
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
</body>
</html>





