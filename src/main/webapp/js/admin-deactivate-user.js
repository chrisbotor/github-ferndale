Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in admin-deactivate-user page!');

    // load the store
    userBeanStore.load();
 
    
    // #######################			FIELD SETS 		########################## //
    
    var searchUserBox = {
            columnWidth: .99,
            layout: 'form',
            align:'center',
            items: [
            {
                xtype: 'fieldset',
                autoHeight: true,
                defaultType: 'textfield',
                items: [
                {
                    fieldLabel: 'USER NAME',
                    name: 'fullName',
                    id : 'fullName',
                    xtype:'combo',
                	store: userBeanStore,
                	queryMode: 'server',
                	displayField:'fullName',
                	valueField: 'id',
                	mode: 'local',
                	typeAhead: true,
                    triggerAction: 'all',
                    lazyRender: true,
                    emptyText: 'Select User',
                    allowBlank: false,
                    blankText: 'User should not be null',
                    anchor: '100%',
                    listeners:{
                    	select:  function(combo, record, index)
                    	{
                    		var userId = record.get('id');
                    		var userName = record.get('username');
                    		
                    		// alert('YEY!');
                    		// alert('userId: ' + userId);
                    		// alert('userName: ' + userName);
                    		
                    		Ext.getCmp('deactivateUser').show();
                    		
                    		// set the values
                    		Ext.getCmp('userId').setValue(userId);
                    		Ext.getCmp('userName').setValue(userName);
                    		
                    		
                    		/*Ext.getCmp('password').setValue(password);
                    		Ext.getCmp('roleId').setValue(record.get('role_id'));
                    		Ext.getCmp('status').setValue(record.get('status'));
                    		Ext.getCmp('multiOwner').setValue(record.get('status'));
                    		Ext.getCmp('createdAt').setValue(record.get('createdAt'));
                    		Ext.getCmp('updatedAt').setValue(record.get('updatedAt'));*/
                    	}
                    }
                },
                {
                	fieldLabel: 'DE-ACTIVATE USER?',
                    cls: 'cTextAlign',
                    xtype: 'checkbox',
                    hidden:true,
                    id:'deactivateUser',
                    name:'deactivateUser'
                 }
              ]
            }
           ]
    	}
    
    
  //############################################## HIDDEN FIELDS ######################################################   
    // DEACTIVATE CHECKBOX
  /*  var deactivateUserCheckbox = {
    	fieldLabel: 'DE-ACTIVATE USER?: ',
        cls: 'cTextAlign',
        xtype: 'checkbox',
        hidden:true,
        id:'deactivateUser',
        name:'deactivateUser'
     }*/
    
    // USER ID
    var userId = {
    	name: 'userId',
    	id: 'userId',
    	xtype: 'numberfield',
        hidden:true,
        // anchor: '100%'
    }
    
    // USERNAME
    var userName = {
    	name: 'userName',
    	id: 'userName',
    	xtype: 'textfield',
        hidden:true,
        //anchor: '100%'
    }
    
    
    // PASSWORD
    var password = {
    	name: 'password',
    	id: 'password',
    	xtype: 'textfield',
        hidden:true,
        // anchor: '100%'
    }
    
    // ROLE ID
    var roleId = {
    	name: 'roleId',
    	id: 'roleId',
    	xtype: 'numberfield',
        hidden:true,
        // anchor: '100%'
    }
    
    
    // STATUS
    var status = {
    	name: 'status',
    	id: 'status',
    	xtype: 'textfield',
        hidden:true,
        // anchor: '100%'
    }
    
    
    // MULTIOWNER
    var multiOwner = {
    	name: 'multiOwner',
    	id: 'multiOwner',
    	xtype: 'textfield',
        hidden:true
        // anchor: '100%'
    }
    
    // CREATED AT
    var createdAt = {
    	name: 'createdAt',
        id: 'createdAt',
        xtype: 'datefield',
        format: 'm/d/Y',
        hidden:true
        // submitFormat: 'm/d/Y',
        // submitFormat: 'Y/m/d',
        // anchor: '100%'
    }
    
    // UPDATED AT
    var updatedAt = {
    	name: 'updatedAt',
        id: 'updatedAt',
        xtype: 'datefield',
        format: 'm/d/Y',
        hidden:true
        // submitFormat: 'm/d/Y',
        // submitFormat: 'Y/m/d',
        // anchor: '100%'
    }
    
    
    
    function fnLoadForm(theForm)
    {
        //for the purpose of this tutorial, load 1 record.
        theForm.getForm().load();
    }
    
    
    var deactivateUserForm = new Ext.FormPanel({
    	name: 'searchUserForm',
        id: 'searchUserForm',
        title: 'Search User',
        style: 'margin-top:40px',
        frame: true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 120,
        labelAlign : 'left',
        renderTo: 'admin-create-request-table',
        width : 400,
        height : 170,
        align:'center',
        items: [{
            align:'center',
            layout: 'column',
            items: [searchUserBox, userId, userName, password, roleId, status, multiOwner, createdAt, updatedAt]
        }],
        buttons: [{
            text: 'Submit',
            handler: function()
            {
            	fnSubmitRequest(deactivateUserForm);
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
            	fnResetForm(deactivateUserForm);
            }
        }]
    });    
    
});

      // submit the form
      function fnSubmitRequest(theForm)
      {
                    	    	theForm.getForm().submit({
                    	            //waitMsg:'Loading...',
                    	            url: 'user/deactivate-user.action',
                    	            waitMsg: 'loading...',
                    	            success: function(form, action) {
                    	                Ext.Msg.alert('Success', 'Successfully de-activated the user.');
                    	                fnResetForm(theForm);
                    	            },
                    	            failure: function(form, action)
                    	            {
                    	                Ext.Msg.alert('Warning', 'De-activating user failed.');
                    	            }
                    	        });
                    	    
                    	    }
                    	    
                    	    
function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} //end fnResetForm