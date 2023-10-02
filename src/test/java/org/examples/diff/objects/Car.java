package org.examples.diff.objects;

import org.examples.diff.anotations.AuditKey;

import java.util.List;

public record Car(String name, List<PartNoId> parts1, List<PartDuplicateId> parts2)  {
}
