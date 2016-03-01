var currentUser = JSON.parse(localStorage.getItem("fullLoginObject"));
console.log(currentUser['userTbl'])
var Lock = function () {

    return {
        //main function to initiate the module
        init: function () {
            //set the details of the user in the lock screen
            var usrId=currentUser['userTbl']['usrId'];
            var usrUsername=currentUser['userTbl']['usrName'];
            var usrDisplayName = currentUser['userTbl']['usrDisplayName'];
            var fullDisplayName = usrDisplayName.split(',');
			fullDisplayName[0] = fullDisplayName[0].replace(/\"/g,"");
			if(null!=fullDisplayName[1])
				fullDisplayName[1] = fullDisplayName[1].replace(/\"/g,"");
			else
				fullDisplayName[1]='';
			usrDisplayName = fullDisplayName[0]+' '+fullDisplayName[1]
            var usrEmail=$.cookie('usrEmail');
            document.getElementById('userName').innerHTML=usrDisplayName;
            // document.getElementById('email').innerHTML=usrEmail;
            document.getElementById('notUserName').innerHTML="Not "+usrDisplayName+"?";
            console.log(usrUsername);
            //to hit the url to unlock
            $("form").submit(function() {
                setTimeout(function()
                {
                var userName=currentUser['userTbl']['usrName'];
                var password = $('#password').val();
                var encriptedPassword=$().crypt({method:"sha1",source:password}) ;
//                console.log("The encripted Password is "+$().crypt({method:"sha1",source:password}) );
                var formData = {usrName : userName, usrPassword : encriptedPassword};
                var inputJsonData = JSON.stringify(formData);
//                console.log("The iput data to the url is:"+inputJsonData);
                login(inputJsonData);
                }, 650);
return false;
            });
            //init background slide image
        //      $.backstretch([
		      //   "theme/assets/admin/pages/media/bg/1.jpg",
    		  //   "theme/assets/admin/pages/media/bg/2.jpg",
    		  //   "theme/assets/admin/pages/media/bg/3.jpg",
    		  //   "theme/assets/admin/pages/media/bg/4.jpg"
		      //   ], {
		      //     fade: 1000,
		      //     duration: 8000
		      // });
        }

    };

}();