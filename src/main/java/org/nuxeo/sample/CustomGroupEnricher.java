package org.nuxeo.sample;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.api.NuxeoGroup;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

/**
 * Enrich {@link nuxeo.ecm.core.api.NuxeoGroup} Json.
 * <p>
 * Format is:
 *
 * <pre>
 * {@code
 * {
 *   ...
 *   "contextParameters": {
 *     "custom_group": { ... }
 *   }
 * }
 * </pre>
 * </p>
 */
@Setup(mode = SINGLETON, priority = REFERENCE)
public class CustomGroupEnricher extends AbstractJsonEnricher<NuxeoGroup> {

    public static final String NAME = "custom_group";

    public CustomGroupEnricher() {
        super(NAME);
    }

    @Override
    public void write(JsonGenerator jg, NuxeoGroup obj) throws IOException {
		List<String> parentGroups = obj.getParentGroups();
		jg.writeFieldName("parentGroups");
		if (parentGroups == null) {
			jg.writeNull();
		} else {
			try {
				jg.writeStartArray();
				for (String s : parentGroups) {
					jg.writeString(s);
				}
			} finally {
				jg.writeEndArray();
			}
		}

    }
}
