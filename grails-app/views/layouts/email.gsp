<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
    <g:layoutHead/>
</head>

<body style="margin:0;">
<table width="100%;" cellpadding="0" cellspacing="0" rowspacing="0" border="0"
       style="margin-top: 0; margin-right: 0; margin-bottom: 0; margin-left: 0; background-color: #cccccc;">
    <tbody>
    <tr>
        <td>
            <table width="640px" cellpadding="20" cellspacing="0" rowspacing="0" border="0"
                   style="margin-top: 50px; margin-right: auto; margin-bottom: 50px; margin-left: auto; background-color: #ffffff;-webkit-border-radius:6px;-moz-border-radius:6px;border-radius:6px;">
                <tbody>
                <tr>
                    <td style="width:10%;background-color: #999999;-webkit-border-top-left-radius:6px;-moz-border-topleft-radius:6px;border-top-left-radius:6px;-webkit-border-bottom-left-radius:6px;-moz-border-bottomleft-radius:6px;border-bottom-left-radius:6px;">
                        &nbsp;
                    </td>
                    <td style="width:90%;">

                        <g:layoutBody/>

                    </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>

</body>
</html>