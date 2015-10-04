Ext.onReady(function(){
    
    Ext.QuickTips.init();
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    var nameAndCompany = {
        columnWidth: .98,
        layout: 'form',
        items: [
        {
            xtype: 'fieldset',
            title: 'Personal',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            
           {
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'FIRST NAME',
                name: 'firstname',
                // readOnly:true,
                disabled: true,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'MIDDLE NAME',
                name: 'middlename',
                // readOnly:true,
                disabled: true,
                anchor: '100%'
            },{
                xtype: 'textfield',
                fieldLabel: 'LAST NAME',
                name: 'lastname',
                // readOnly:true,
                disabled: true,
                cls: 'cTextAlign',
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                fieldLabel: 'BIRTHDAY',
                name: 'birthdate',
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                allowBlank: false,
                blankText: 'BirthDay should not be null',
                editable:false,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype:'combo',
                fieldLabel: 'CIVIL STATUS',
                name:'civilStatus',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Single', 'Single'],
                    ['Married', 'Married']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                emptyText: 'Select action',
                allowBlank: false,
                blankText: 'Civil status should not be null',
                anchor: '100%'
            }
            ]
        }]
    }
    
    var internet = {
        columnWidth: .98,
        layout: 'form',
        items: [
        {
            xtype: 'fieldset',
            title: 'Phone and Email Address',
            autoHeight: true,
            defaultType: 'textfield',
            items: [{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'MOBILE NUMBER',
                name: 'mobileNumber',
                allowBlank:true,
                // minLength:11,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'HOME NUMBER',
                name: 'homeNumber',
                allowBlank:true,
                // minLength:7,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                vtype: 'email',
                fieldLabel: 'EMAIL ADDRESS',
                name: 'emailAddress',
                allowBlank:true,
                anchor: '100%'
            }]
        }]
    }
    
    var work = {
        columnWidth: .98,
        layout: 'form',
        items: [{
            xtype: 'fieldset',
            title: 'Work Details',
            //autoHeight: true,
            defaultType: 'textfield',
            items: [{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'COMPANY NAME',
                name: 'workName',
                allowBlank:true,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'WORK LANDLINE',
                name: 'workLandline',
                allowBlank:true,
                // minLength:7,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'WORK MOBILE',
                name: 'workMobile',
                allowBlank:true,
                // minLength:11,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                vtype: 'email',
                fieldLabel: 'WORK EMAIL',
                name: 'workEmail',
                allowBlank:true,
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textarea',
                fieldLabel: 'WORK ADDRESS',
                name: 'workAddress',
                allowBlank:true,
                anchor: '100%'
            }]
        }]
    }
    
    var contactForm = new Ext.FormPanel({
        title: 'UPDATE PROFILE',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 105,
        labelAlign : 'left',
        width: 500,
        height: 590,
        renderTo: 'my-tabs',
        items: 
        	[ nameAndCompany, internet, work],
       buttons: [{
            id: 'mf.btn.add',
            text: 'Submit',
            handler: function() {
                fnUpdateForm(contactForm);
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(contactForm);
            }
        }]
    });
    
    fnLoadForm(contactForm);
    
    var Occupant = Ext.data.Record.create([
    {
        name: 'id'
    },

    {
        name: 'houseId',
        type: 'int'
    },
    {
        name: 'lastname',
        type: 'string'
    },
    {
        name: 'firstname',
        type: 'string'
    },
    {
        name: 'middlename',
        type: 'string'
    },
    {
        name: 'relationTo',
        type: 'string'
    }]);


    var oproxy = new Ext.data.HttpProxy({
        api: {
            read : 'occupant/view.action',
            create : 'occupant/create.action',
            update: 'occupant/update.action',
            destroy: 'occupant/delete.action'
        }
    });
    
    var oreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    Occupant);
    
    // The new DataWriter component.
    var owriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    // Typical Store collecting the Proxy, Reader and Writer together.
    var ostore = new Ext.data.Store({
        id: 'user',
        proxy: oproxy,
        reader: oreader,
        writer: owriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
    ostore.load();
    
    
    //Ext.data.DataProxy.addListener('exception', function(oproxy, type, action, options, res) {
        //Ext.Msg.show({
            //title: 'ERROR',
            //msg: res.message,
            //icon: Ext.MessageBox.ERROR,
            //buttons: Ext.Msg.OK
        //});
    //});
    
    
    var oeditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
    //create owner grid
    var oGrid = new Ext.grid.GridPanel({
        store: ostore,
        columns: [
        {
            header: "ID",
            width: 50,
            hidden:true,
            sortable: true,
            dataIndex: 'Id',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
        },

        {
            header: "HOUSE ID",
            width: 50,
            hidden:true,
            sortable: true,
            dataIndex: 'houseId',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
        },
        {
            header: "LAST NAME",
            width: 100,
            sortable: true,
            dataIndex: 'lastname',
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },
        {
            header: "FIRST NAME",
            width:100,
            sortable: true,
            dataIndex: 'firstname',
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },
        {
            header: "MIDDLE NAME",
            width:100,
            sortable: true,
            dataIndex: 'middlename',
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },
        {
            header: "RELATION TO",
            width:100,
            sortable: true,
            dataIndex: 'relationTo',
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Husband', 'Husband'],
                    ['Wife', 'Wife'],
                    ['Children', 'Children'],
                    ['Mother', 'Mother'],
                    ['Father', 'Father'],
                    ['Brother', 'Brother'],
                    ['Sister', 'Sister'],
                    ['Other', 'Other'],
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                emptyText: 'Select action'
            }
        }
        ],
        plugins: [oeditor],
        title: 'Our Occupant',
        frame:true,
        cls:'x-panel-blue',
        width:400,
        height:300,
        tbar: [{
            iconCls: 'icon-user-add',
            text: 'Add Occupant',
            handler: function(){
                var e = new Occupant({
                    lastname: '',
                    firstname: '',
                    middlename: '',
                    relationTo:''
                });
                oeditor.stopEditing();
                ostore.insert(0, e);
                oGrid.getView().refresh();
                oGrid.getSelectionModel().selectRow(0);
                oeditor.startEditing(0);
            }
        }
        ,{
            iconCls: 'icon-user-delete',
            text: 'Remove Occupant',
            handler: function(){
                oeditor.stopEditing();
                var s = oGrid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    ostore.remove(r);
                }
            }
        }
        ,{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                ostore.save();
            }
        }]
    });

    var myTabPanel = new Ext.TabPanel({
         //title:"MY TAB",
        //width : 800,
        //height : 450,
        //align:'center',
        //renderTo: 'my-tabs',
        //activeItem: 0,
    //items: [contactForm]
    });

});


function fnLoadForm(theForm) 
{
    //for the purpose of this tutorial, load 1 record.
    theForm.getForm().load({
        url: 'load.action',
        
        waitMsg: 'loading...',
        success: function(form, action) {
            //Ext.Msg.alert('Success', action.result)
            Ext.getCmp('mf.btn.add').setDisabled(false);		
            Ext.getCmp('mf.btn.reset').setDisabled(false);	
        },
        failure: function(form, action) {
            Ext.Msg.alert('Warning', 'Error Unable to Load Form Data.'); //action.result.errorMessage
        }
    });
} //end fnLoadForm


function fnUpdateForm(theForm)
{
    //if(theForm.isValid()){
    theForm.getForm().submit({
        //waitMsg:'Loading...',
        url: 'update.action',
        waitMsg: 'loading...',
        success: function(form, action) {
            Ext.Msg.alert('Success', 'Profile updated successfully');
        },
        failure: function(form, action) {
            Ext.Msg.alert('Warning', 'Profile encountered an error');
        }
    });
//}
} //end fnUpdateForm
function fnResetForm(theForm)
{
    //theForm.getForm().reset();
    fnLoadForm(theForm)
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} //end fnResetForm