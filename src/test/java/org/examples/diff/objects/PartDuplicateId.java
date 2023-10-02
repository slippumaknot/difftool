package org.examples.diff.objects;

import org.examples.diff.anotations.AuditKey;

public record PartDuplicateId(@AuditKey String key, String id , String name)  {
}
