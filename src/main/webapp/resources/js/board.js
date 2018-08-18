function update(message, channel, event) {
    var data = JSON.parse(message);
    var rideId = data["rideId"];
    var type = data["type"];
    if (type === "update") {
        $("#table>tbody>tr").each(function () {
            var $row = $(this);
            if ($row.find("td").first().text() == rideId) {
                var $table = $row.find("td:eq(1)");
                $table.find("tr").each(function () {
                    var city = $(this).find("td").first().text();
                    $(this).find("td").last().text(data["stations"][city]);
                })
            }
        });
    } else if (type === "add"){
        var table = $("#table>tbody");
        var row = $("<tr></tr>");
        row.append($("<td>" + rideId + "</td><td></td>"));

        var innerTable = $('<table class="table">' +
            '<thead>' +
            '<tr>' +
            '<th scope="col">Station</th>' +
            '<th scope="col">Time</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody></tbody>' +
            '</table>');

        Object.keys(data["stations"]).forEach(function (key) {
            var innerRow = $('<tr></tr>');
            innerRow.append($('<td>' + data["stations"][key]["station"] + '</td>'));
            innerRow.append($('<td>' + data["stations"][key]["time"] + '</td>'));
            innerTable.find('tbody').append(innerRow);
        });

        row.find('td').last().append(innerTable);
        table.append(row);
    } else if (type === "delete"){
        $("#table>tbody>tr").each(function () {
            var $row = $(this);
            if ($row.find("td").first().text() == rideId) {
                $row.remove();
            }
        });
    }
}