
Ext.onReady(function(){

    Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
   
    var HouseBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },{
        name: 'userId',
        type: 'int'
    },
    {
        name: 'address',
        type: 'string'
    }, {
        name: 'owner',
        type: 'string'
    },{
        name: 'rented',
        type: 'string'
    }]);

    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'house/view.action'
        }
    });

    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    HouseBean);
    
    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

    // Typical Store collecting the Proxy, Reader and Writer together.
    var mystore = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,
        writer: writer,
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });

    mystore.load();
    
    var existingOwner = {
        columnWidth: .98,
        layout: 'form',
        align:'center',
        items: [
        {
            xtype: 'fieldset',
            //title: 'Existing Owner',
            autoHeight: true,
            //defaultType: 'textfield', 
            items: [
            {
                xtype:'combo',
                cls: 'cTextAlign', 
                queryMode: 'server',
                store: mystore,
                fieldLabel: 'ADDRESS',
                displayField:'address',
                hiddenName: 'id',
                valueField: 'id',
                mode: 'local',
                // typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                // editable:false,
                emptyText: 'Select action',
                allowBlank: false,
                anchor: '100%',
                listeners:{
                    select: function(combo, record, index) {
                            var owner = record.get('owner')
                            var rented = record.get('rented')
                            var userId = record.get('userId')
                            Ext.getCmp('ownerId').setValue(owner)
                            Ext.getCmp('rentedId').setValue(rented)
                            Ext.getCmp('userIds').setValue(userId)
                        }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'OWNER',
                name: 'owner',
                id:'ownerId',
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'USERID',
                name: 'userId',
                id:'userIds',
                anchor: '100%',
                hidden:true
            },{
                fieldLabel: 'RENTED',
                name: 'rented',
                id:'rentedId',
                cls: 'cTextAlign',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Y', 'Y'],
                    ['N', 'N']
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
                blankText: 'Rented should not be null',
                anchor: '100%',
                listeners:{
                    select: function(combo, record, index) {
                            if(combo.getValue() == 'Y'){
                                Ext.getCmp('idfirstname').show();
                                Ext.getCmp('idmiddlename').show();
                                Ext.getCmp('idlastname').show();
                                Ext.getCmp('ownerId').hide();
                            }else{
                                Ext.getCmp('idfirstname').hide();
                                Ext.getCmp('idmiddlename').hide();
                                Ext.getCmp('idlastname').hide();
                                Ext.getCmp('ownerId').show();
                            }
                            
                        }
                }
            },{
            cls: 'cTextAlign',
            fieldLabel: 'MOVE IN DATE',
            name: 'moveIn',
            xtype: 'datefield',
            format: 'm/d/Y',
            submitFormat: 'm/d/Y',
            editable:false,
            anchor: '100%'
        },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'FIRST NAME',
                name: 'firstname',
                id:'idfirstname',
                anchor: '100%',
                hidden:true
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'MIDDLE NAME',
                name: 'middlename',
                id:'idmiddlename',
                anchor: '100%',
                hidden:true
            },{
                xtype: 'textfield',
                fieldLabel: 'LAST NAME',
                name: 'lastname',
                id:'idlastname',
                cls: 'cTextAlign',
                anchor: '100%',
                hidden:true
            }
            ]
        }]
    }
    
    var newLesse = {
        columnWidth: .5,
        layout: 'form',
        id:'newOwner',
        items: [
        {
            xtype: 'fieldset',
            title: 'Owner',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
              
            {
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'FIRST NAME',
                name: 'firstname',
                id:'idfirstname',
                anchor: '100%'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'MIDDLE NAME',
                name: 'middlename',
                id:'idmiddlename',
                anchor: '100%'
            },{
                xtype: 'textfield',
                fieldLabel: 'LAST NAME',
                name: 'lastname',
                id:'idlastname',
                cls: 'cTextAlign',
                anchor: '100%'
            }
            ]
        }]
    }

    //var tpl = new Ext.XTemplate('<tpl for="."><div class="x-combo-list-item" >{firstname} {middlename} {lastname}</div></tpl>');
    //Ext.getCmp('myLabel').setVisible(false);
    

    var mf = new Ext.FormPanel({
        title: 'MOVE IN FORM',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 100,
        labelAlign : 'center',
        width : 450,
        height : 300,
        renderTo: 'my-tabs',
        items: [{
            align:'left',
            layout: 'column',
            items: [existingOwner]
        }],
        buttons: [{
            id: 'mf.btn.add',
            text: 'Submit',
            handler: function() {
                fnAddForm(mf);
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(mf);
            }
        }]
    });
    

    var myTabPanel = new Ext.TabPanel({
        //title:"MY TAB",
        //width : 800,
        //height : 350,
        //align:'center',
        //renderTo: 'my-tabs',
        //activeItem: 0,
        //items: [mf]
        });

});

function fnLoadForm(theForm)
{
    //for the purpose of this tutorial, load 1 record.
    theForm.getForm().load({
        //url:'loadOwner.action',
        success: function(form, action) {
        },
        failure: function(form, action) {
            //Ext.getCmp('myLabel').hide();
            //Ext.getCmp('ownerId').clear();//action.result.errorMessage
        }
    });
} //end fnLoadForm

function fnAddForm(theForm)
{
    theForm.getForm().submit({
        url: 'admin-moveLesse.action',
        waitMsg: 'loading...',
        success: function(form, action) {
            Ext.Msg.alert('Success', 'Information saved successfully.');
            fnResetForm(theForm);
        },
        failure: function(form, action) {
            Ext.Msg.alert('Warning', 'Error in Saving Data');
        }
    });
}


function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} //end fnResetForm
