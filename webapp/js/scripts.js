$(".answerWrite input[type=submit]").click(addAnswer)
$(".qna-comment button[type=submit]").click(deleteAnswer)

function deleteAnswer(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  var queryString = deleteBtn.closest("form").serialize();
  console.log("qs : " +queryString);

  $.ajax({
    type: 'post',
    url: '/api/qna/deleteAnswer',
    data: queryString,
    dataType: 'json',
    error: function (xhr, status) {
      alert("error");
    },
    success : function (json, status) {
      console.log("json : " + json);
      // console.log("json.result : " +json.result)
      var result = json;
      if(result.status) {
        deleteBtn.closest('article').remove()
      }
    }
  })

}

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError(),
    success: onSuccess()
  });
}

function onSuccess(json, status){
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(
      /{(\d+)}/g,
      function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match;
  });
};
