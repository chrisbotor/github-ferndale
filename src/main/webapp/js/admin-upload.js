Ext.onReady(function(){
    Ext.QuickTips.init();

    // Create a variable to hold our EXT Form Panel.

    // Assign various config options as seen.
    var login = new Ext.FormPanel({
        labelWidth:80,
        url:'upload-water-reading.action',
        frame:true,
        title:'File Uploader',
        renderTo : 'login',
        defaultType:'textfield',
        width:300,
        height:150,
        monitorValid:true,
        items:[/*{
            fieldLabel:'Password',
            name:'password',
            inputType:'password',
            allowBlank:false
        },*/
        {
        	fieldLabel : 'File',
        	name : 'file',
        	allowBlank : false,
        	inputType : 'file',
        	fileUpload : true
        	}],

        // All the magic happens after the user clicks the button
        buttons:[{

            text:'Submit',
            formBind: true,
            // Function that fires when user clicks the button
            handler:function(){
                login.getForm().submit({

                    method:'POST', 
                    success:function(form, action){				
                      
                    },

                    // Failure function, see comment above re: success and failure.
                    failure:function(form, action){
                        if(action.failureType == 'server'){
                            var json = Ext.util.JSON.decode(action.response.responseText);
                      
                            Ext.Msg.alert('Status', 'Uploaad Failed!', function(btn, text){

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

    var fu = Ext.get('uploadWaterFile');
    
   // login.render('login');
    
   /* var el = Ext.get('uploadWaterFile');
    el.on('change', alert("changed!"))*/;

});
