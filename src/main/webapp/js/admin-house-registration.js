
Ext.onReady(function(){

    Ext.QuickTips.init();

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    var Owner = Ext.data.Record.create([
    {
        name: 'id'
    },

    {
        name: 'fullName',
        type: 'string'
    }]);

    var ownerproxy = new Ext.data.HttpProxy({
        api: {
            read : 'owner/fullname/view.action'
        }
    });

    var ownerreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    Owner);

    // The new DataWriter component.
    var ownerwriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

    // Typical Store collecting the Proxy, Reader and Writer together.
    var ownerstore = new Ext.data.Store({
        id: 'user',
        proxy: ownerproxy,
        reader: ownerreader,
        writer: ownerwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    ownerstore.load();

    var lotDetails = {
    	columnWidth: .98,
        layout: 'form',
        items: [
        {
            xtype: 'fieldset',
            title: 'Lot Details',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            {
                xtype: 'textfield',
                fieldLabel: 'ADDRESS NUMBER',
                name: 'addressNumber',
                cls: 'cTextAlign',
                anchor: '100%',
                allowBlank: false,
                blankText: 'Address Number should not be null'

            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'ADDRESS STREET',
                name: 'addressStreet',
                anchor: '100%',
                allowBlank: false,
                blankText: 'Address Street should not be null'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'LOT AREA',
                name: 'lotArea',
                anchor: '100%',
                allowBlank: false,
                blankText: 'Lot Area should not be null'
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'TITLE',
                name: 'title',
                anchor: '100%',
                allowBlank: false,
                blankText: 'Title should not be null'
            },
            {
                
                cls: 'cTextAlign',
                xtype: 'checkbox',
                fieldLabel: 'EXISTING OWNER ? ',
                name: 'existing',
                anchor: '100%',
                unchecked: true,
                inputValue: 'existing',
                handler: function(obj) {
                    if( obj.checked ) {
                        Ext.getCmp('newOwner').hide();
                        Ext.getCmp('myLabel').show();
                        Ext.getCmp('ownerId').reset();
                    } else {
                        Ext.getCmp('newOwner').show();
                        Ext.getCmp('myLabel').hide();
                        Ext.getCmp('idlastname').reset();
                        Ext.getCmp('idfirstname').reset();
                        Ext.getCmp('idmiddlename').reset();
                        Ext.getCmp('ownerId').setValue('');
                    }
                }


            },
            ]
        }]
    }
    
    var newOwner = {
    	columnWidth: .98,
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
    var existingOwner = {
    	columnWidth: .98,
        layout: 'form',
        id:'myLabel',
        setVisible:false,
        items: [
        {
            xtype: 'fieldset',
            title: 'Existing Owner',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            {
                xtype:'combo',
                id:'ownerId',
                cls: 'cTextAlign',
                queryMode: 'server',
                store: ownerstore,
                fieldLabel: 'Owner Name',
                //tpl: '<tpl for="."><div class="x-combo-list-item">{firstname} {middlename} {lastname}</div></tpl>',
                displayField:'fullName',
                hiddenName: 'id',
                valueField: 'id',
                mode: 'local',
                // typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                // editable:false,
                emptyText: 'Select action',
                defaultValue:'0',
                anchor: '100%',
                listeners:{
                	select:  function(combo, record, index)
                	{
                		var existingOwner = combo.getValue();
               			// alert('existingOwner: ' + existingOwner);
               			
               			Ext.getCmp('existingOwnerId').setValue(existingOwner);
               		}
                }
            },
            {
                xtype: 'textfield',
                name: 'existingOwnerId',
                id:'existingOwnerId',
                hidden: true
            }
          ]
        }]
    }
    

    var mf = new Ext.FormPanel({
        title: 'HOUSE OWNER FORM',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 140,
        labelAlign : 'left',
        width : 450,
        height : 450,
        renderTo: 'my-tabs',
        items: [{
            align:'center',
            layout: 'column',
            items: [lotDetails,newOwner,existingOwner]
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
   
    fnLoadForm(mf);
    

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

        success: function(form, action) {
        },
        failure: function(form, action) {
            Ext.getCmp('myLabel').hide();
            // Ext.getCmp('ownerId').clear();//action.result.errorMessage  // commented out because it causes an error, clear() is not a function
        }
    });
} //end fnLoadForm

function fnAddForm(theForm)
{
    theForm.getForm().submit({
        url: 'admin-house-registration/add-house-owner.action',
        waitMsg: 'Registering new house and owner',
        success: function(form, action) {
        	removeXButton(Ext.Msg.alert('Success', 'Successfully added new House and Owner'));
            fnResetForm(theForm);
        },
        failure: function(form, action) {
        	removeXButton(Ext.Msg.alert('Warning', 'Error in saving new House and Owner.'));
        }
    });
}


function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    // Ext.getCmp('mf.btn.reset').setDisabled(false); // this causes javascript error because it is undefined
} //end fnResetForm
