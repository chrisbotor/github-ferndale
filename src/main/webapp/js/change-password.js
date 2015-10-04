Ext.onReady(function(){
	Ext.QuickTips.init();

	
	// these are the config options for the change password form
	var changePasswordForm = new Ext.FormPanel({
		labelWidth:80,
		url:'update-password.action',
		frame:true,
		title:'Change Your Password',
		renderTo: 'login',

		defaultType:'textfield',
		width:300,
		height:150,
		monitorValid:true,
		// Specific attributes for the text fields for username / password.
		// The "name" attribute defines the name of variables sent to the server.

		items:[{
			fieldLabel:'Password',
			name:'password1',
			id: 'pass1',
			inputType:'password',
            allowBlank:false
		},{
			fieldLabel:'Confirm Password',
			name:'password2',
            id: 'pass2',    
			inputType:'password',
			allowBlank:false
		}],
		listeners : {
            'render' : function(cmp)
            {
                cmp.getEl().on('keypress', function(e)
                {
                    if (e.getKey() == e.ENTER)
                    {
                    	// alert('ENTER PRESSED!');
                    	
                    	var pass1 = Ext.getCmp('pass1').getValue();
                        var pass2 = Ext.getCmp('pass2').getValue();
                       
                        if(pass1 == pass2)
                        {
                        	fnSubmitRequest(changePasswordForm);
                        }
                        else
                        {
                        	Ext.Msg.alert('Error','Passwords do not match');
                        	Ext.getCmp('pass1').reset();
                        	Ext.getCmp('pass2').reset();
                        }
                  }
               });
            }
        },

		// Password should be changed before the user can proceed
        // this is the case where the user clicks the "Submit" button
		buttons:[{

			text:'Submit',
			formBind: true,
			// Function that fires when user clicks the button
			handler:function()
					{
						var pass1 = Ext.getCmp('pass1').getValue();
		                var pass2 = Ext.getCmp('pass2').getValue();
		               
		                if(pass1 == pass2)
		                {
		                	fnSubmitRequest(changePasswordForm);
		                }
		                else
		                {
		                	Ext.Msg.alert('Error','Passwords do not match');
		                	Ext.getCmp('pass1').reset();
		                	Ext.getCmp('pass2').reset();
		                }
					}
		}]
	});

});



//SUBMIT THE FORM
function fnSubmitRequest(theForm)
{
	theForm.getForm().submit(
	{
		url: 'update-password.action',
		waitMsg: 'updating the password...',
		method:'POST', 
		success:function(form, action)
		{				
			var json = Ext.util.JSON.decode(action.response.responseText);
			var roleId = json.data.roleId;
			var pwd = json.data.password;
    	            
			if(pwd == '123456')
			{
				Ext.Msg.alert('Error','Password was not changed! <br /> Please enter another password', function(btn, text)
				{
					window.location = 'change-password.action';
					login.getForm().reset();
				});
			}
			else
			{
				Ext.Msg.alert('Status','Change Password Successful!', function(btn, text)
				{
					// alert('roleId: ' + roleId);
    	            
					// if role is admin, redirect to admin-home page
					if (roleId == 1)
					{
						window.location = 'admin-home.action';
					}
					else
					{
						window.location = 'home-owner-landing-page.action';
					}
				});
			}
		},
		failure:function(form, action)
		{
			Ext.Msg.alert('Status','Change Password Failed!')
			window.location = 'change-password.action';
			login.getForm().reset();
    	}
	});
}