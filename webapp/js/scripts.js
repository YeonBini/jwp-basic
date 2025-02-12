// $("#update-question").on('click', updateQuestion);
//
// function updateQuestion(e) {
//   e.preventDefault();
//
//   var url = $("#update-question")[0].href;
//   console.log(url)
//
//   $.ajax({
//     type: 'get',
//     url: url,
//     success : function (json, status) {
//       alert(json.message);
//     },
//     error : function (xhr, status) {
//       alert("error!");
//     }
//   })
// }


$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();
  console.log(queryString);

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success: onSuccess
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
  $(".qna-comment-count").html("<strong>" + json.question.countOfComment +"</strong>개의 의견")
}

function onError(xhr, status) {
  alert("error");
}

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
      var result = json.answer;
      if(result.status) {
        deleteBtn.closest('article').remove()
      }

      $(".qna-comment-count").html("<strong>" + json.question.countOfComment +"</strong>개의 의견")
    }
  })

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
