document.addEventListener('DOMContentLoaded', function () {
  var calendarEl = document.getElementById('calendar');

  // $.ajax({
  //   url: "data.json",
  //   type: "get",
  //   dataType: 'json',
  //   success: function (data) {
  //     console.log(data[0].title);
  //   }
  // })

  var calendar = new FullCalendar.Calendar(calendarEl, {
    plugins: ['interaction', 'dayGrid'],
    header: {
      left: 'prevYear,prev,next,nextYear today',
      center: 'title',
      right: 'dayGridMonth,dayGridWeek,dayGridDay'
    },
    //defaultDate: '2020-02-12',
    navLinks: true, // can click day/week names to navigate views
    editable: true,
    eventLimit: true, // allow "more" link when too many events
    eventSources: [{
      events: function (info, successCallback, failureCallback) {
        $.ajax({
          url: "data.json",
          type: "get",
          dataType: 'json',
          success: function (data) {
            successCallback(data); //json데이터 화면에 뿌려줌
          }
        })
      }
    }]
    // events: [
    //   {
    //     title: data[0].title,
    //     start: data[0].start
    //   },
    //   {
    //     title: 'Long Event',
    //     start: '2020-05-07',
    //     end: '2020-05-10'
    //   }
    //   // {
    //   //   groupId: 999,
    //   //   title: 'Repeating Event',
    //   //   start: '2020-02-16T16:00:00'
    //   // },
    //   // {
    //   //   title: 'Click for Google',
    //   //   url: 'http://google.com/',
    //   //   start: '2020-02-28'
    //   // }
    // ]
  });

  calendar.render();
});