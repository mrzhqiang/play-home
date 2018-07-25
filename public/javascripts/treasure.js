var treasureList = undefined;

function listTreasure() {
  var url = "/v1/treasures";
  $.ajax({
    type: "get", async: false, url: url, timeout: 3000, success: function (datas) {
      treasureList = eval(datas);
    }, error: function () {
      // todo
    }
  })
}