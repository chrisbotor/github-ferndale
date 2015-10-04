Ext.onReady(function(){
	
	// alert('on login.js!');
	
    Ext.QuickTips.init();

    // LOGIN box
    var login = new Ext.FormPanel({
        labelWidth:80,
        url:'login/validate.action',
        frame:true,
        title:'Please Login',

        defaultType:'textfield',
        width:300,
        height:150,
        monitorValid:true,
        // Specific attributes for the text fields for username / password.
        // The "name" attribute defines the name of variables sent to the server.

        items:[{
            fieldLabel:'Username',
            name:'username',
            allowBlank:false
        },{
            fieldLabel:'Password',

            name:'password',
            inputType:'password',
            allowBlank:false
        }],
        listeners : {
            'render' : function(cmp) {
                cmp.getEl().on('keypress', function(e) {
                    if (e.getKey() == e.ENTER) {
                    	
                    	// alert('ENTER PRESSED!');
                    	
                    	login.getForm().submit({

                            method:'POST', 
                            success:function(form, action)
                            {				
                                var json = Ext.util.JSON.decode(action.response.responseText);
                                var roleId = json.data.roleId;
                                var pwd = json.data.password;
                                
                                
                                if(pwd == '123456')
                                {
                                	window.location = 'change-password.action';
                                }
                                else
                                {
                                	if(roleId == 1)
                                	{
                                		window.location = 'admin-home.action';
                                    }
                                    else
                                    {
                                    	// THIS IS FOR THE MULTIPLE HOUSE CASE (every owner has this landing page)
                                        window.location = 'home-owner-landing-page.action';
                                    }	
                                }
                            },
                            failure:function(form, action)
                            {
                                if(action.failureType == 'server'){
                                    var json = Ext.util.JSON.decode(action.response.responseText);
                              
                                    Ext.Msg.alert('Status', 'Login Failed! Please check your username and password.', function(btn, text){

                                        if (btn == 'ok'){ //add condition here whether it will be redirected to user or to admin (get clue from the response)
                                            //window.location = 'admin.action';
                                            login.getForm().reset();
                                        }
                                    });
        					
                                }else{
                                    Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText);

                                }
                                login.getForm().reset();
                            } 

                        });

                    	
                       // submitLoginForm();
                    }
                });
            }
        },
        
        buttons:[{

            text:'Login',
            formBind: true,
            // Function that fires when user clicks the button
            handler:function(){
                login.getForm().submit({

                    method:'POST', 
                    success:function(form, action)
                    {				
                        var json = Ext.util.JSON.decode(action.response.responseText);
                        var roleId = json.data.roleId;
                        var pwd = json.data.password;
                                        
                       
                            if(pwd == '123456')
                            {
                                window.location = 'change-password.action';
                            }else
                            {
                            	if(roleId == 1)
                            	{
                            		window.location = 'admin-home.action';
                            	}
                            	else
                            	{
                            		// TODO: check if multiple house
                                    //window.location = 'home.action';
                                    window.location = 'home-owner-landing-page.action';
                                }	
                            }
                    },
                    
                    failure:function(form, action)
                    {
                        if(action.failureType == 'server'){
                            var json = Ext.util.JSON.decode(action.response.responseText);
                      
                            Ext.Msg.alert('Status', 'Login Failed!', function(btn, text){

                                if (btn == 'ok'){ //add condition here whether it will be redirected to user or to admin (get clue from the response)
                                    //window.location = 'admin.action';
                                    login.getForm().reset();
                                }
                            });
					
                        }else{
                            Ext.Msg.alert('Warning!', 'Authentication server is unreachable : ' + action.response.responseText);

                        }
                        login.getForm().reset();
                    } 

                });
            }
        }]
    });

    login.render('login');
    
    
    // SUBMIT FORM
    /*function submitLoginForm()
    {
    	login.getForm().submit({
    		method:'POST', 
            success:function(form, action){				
                var json = Ext.util.JSON.decode(action.response.responseText);
                var pwd = json.data.password;
                var roleId = json.data.roleId;
                
                console.log('roleId ' + roleId);
                
                	if(pwd == '123456'){
                        window.location = 'changepassword.html';
                    }else{
                    	if(roleId == 1){
                    		window.location = 'admin-home.html';
                    	}
                    	else
                    	{
                    		// window.location = 'home.action';
                    		window.location = 'home-owner-landing-page.html';
                        }	
                    }
            },
            // Login Failed
            failure:function(form, action){
                if(action.failureType == 'server'){
                    var json = Ext.util.JSON.decode(action.response.responseText);
              
                    Ext.Msg.alert('Status: Login Failed', 'Invalid Username or Password! <br /> Please check your login details and try again.', 
                    		function(btn, text){
                    			if (btn == 'ok')
                    			{   //add condition here whether it will be redirected to user or to admin (get clue from the response)
                    				//window.location = 'admin.action';
                                    login.getForm().reset();
                    			}
                    		});
			
                }
                else
                {
                    Ext.Msg.alert('Login Failed!', 'Cannot connect to the server: ' + action.response.responseText);

                }
                
                login.getForm().reset();
            }
    	});
    }
*/
});
