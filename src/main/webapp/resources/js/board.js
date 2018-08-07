function update(message, channel, event) {
    var data = JSON.parse(message);
    var rideId = data["rideId"];
    $("#table>div>table>tbody>tr").each(function () {
        var $row = $(this);
        if ($row.find("td").first().text() == rideId){
            var $table = $row.find("td:eq(1)");
            $table.find("tr").each(function () {
                var city = $(this).find("td").first().text();
                $(this).find("td").last().text(data["stations"][city]);
            })
        }
    });
}