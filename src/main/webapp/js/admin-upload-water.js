Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    
    //FILE UPLOAD FORM
    Ext.create('Ext.form.Panel', {
        renderTo: 'fi-form',
        width: 500,
        frame: true,
        title: 'File Upload Form',
        bodyPadding: '10 10 0',

        defaults: {
            anchor: '100%',
            allowBlank: false,
            msgTarget: 'side',
            labelWidth: 50
        },

        items: [{
            xtype: 'textfield',
            fieldLabel: 'Name'
        },{
            xtype: 'filefield',
            id: 'form-file',
            emptyText: 'Select an image',
            fieldLabel: 'Photo',
            name: 'photo-path',
            buttonText: '',
            buttonConfig: {
                iconCls: 'upload-icon'
            }
        }],

        buttons: [{
            text: 'Save',
            handler: function(){
                var form = this.up('form').getForm();
                if(form.isValid()){
                    form.submit({
                        url: 'file-upload.php',
                        waitMsg: 'Uploading your photo...',
                        success: function(fp, o) {
                            msg('Success', 'Processed file "' + o.result.file + '" on the server');
                        }
                    });
                }
            }
        },{
            text: 'Reset',
            handler: function() {
                this.up('form').getForm().reset();
            }
        }]
    });
    
        var treeDivs = '<div id="amenity-tree-div"></div>' +
		'<div id="service-tree-div"></div>' +
		'<div id="employee-tree-div"></div>' +
		'<div id="vehicle-tree-div"></div>';
    
    var adminBorderPanel = new Ext.Panel({
	    renderTo: 'my-tabs',
	    width: 1200,
        height: 530,
	    title: 'Portal Admin Menu',
	    layout:'border',
	    defaults: {
	        collapsible: true,
	        split: true,
	        bodyStyle: 'padding:15px'
	    },
	    items: [{
	        title: 'Alerts List',
	        region:'west',
	        margins: '5 0 0 0',
	        cmargins: '5 5 0 0',
	        width: 300,
	        minSize: 100,
	        maxSize: 250,
                html: treeDivs
	    },{
	        title: 'Main Content',
	        collapsible: false,
	        region:'center',
	        margins: '5 0 0 0',
	        xtype: 'tabpanel',
	        activeTab: 0, // index or id
	        items: []
	    }]
	});

 //Amenity Tree
	var amenityTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/amenity/view.action'
			}),
		root : {
			text : 'Amenity Requests',
			id : 'data',
			cls: 'folder',
			expanded : true
		},
		renderTo: 'amenity-tree-div'
	});

//Service Tree
	var serviceTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/service/view.action'
			}),
		root : {
			text : 'Service Requests',
			id : 'data',
			cls: 'folder',
			expanded : true
		},
		renderTo: 'service-tree-div'
	});

//Employee Tree
	var employeeTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/employee/view.action'
			}),
		root : {
			text : 'Employee Updates',
			id : 'data',
			cls: 'folder',
			expanded : true
		},
		renderTo: 'employee-tree-div'
	});

	//Vehicle Tree
	var vehicleTree = new Ext.tree.TreePanel({
		xtype : 'treepanel',
		autoScroll : true,
		loader : new Ext.tree.TreeLoader({
				url : 'alert/vehicle/view.action'
			}),
		root : {
			text : 'Vehicle Updates',
			id : 'data',
			cls: 'folder',
			expanded : true
		},
		renderTo: 'vehicle-tree-div'
	});

});