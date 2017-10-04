package org.nuxeo.sample;

import org.apache.commons.lang3.StringUtils;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.directory.Directory;
import org.nuxeo.ecm.directory.Session;
import org.nuxeo.ecm.directory.api.DirectoryService;


/**
 *
 */
@Operation(id=UpdateGroupField.ID, category=Constants.CAT_SERVICES, label="UpdateGroupField", description="Describe here what your operation does.")
public class UpdateGroupField {

    public static final String ID = "Directory.UpdateGroupField";
    public static final String GROUP_DIRECTORY_NAME = "groupDirectory";

    @Context
    protected CoreSession session;

    @Context
    protected DirectoryService directoryService;

    @Param(name = "groupName", required = true)
    protected String groupName;

    @Param(name = "field", required = true)
    protected String field;

    @Param(name = "value", required = true)
    protected String value;

    @OperationMethod
    public void run() {
        Directory directory = directoryService.getDirectory(GROUP_DIRECTORY_NAME);

        try (Session session = directoryService.open(GROUP_DIRECTORY_NAME)) {
            DocumentModel doc = session.getEntry(groupName);
            if (doc != null) {
                doc.setProperty(directory.getSchema(), field, value);
                session.updateEntry(doc);
            }
        }
    }
}
