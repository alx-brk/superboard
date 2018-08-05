
$("tr").click(function () {
    $("tr").removeClass("tr-chosen");
    $(this).addClass("tr-chosen");
    var id = $(this).find($("th")).text();
    $("#route").val(id);
});