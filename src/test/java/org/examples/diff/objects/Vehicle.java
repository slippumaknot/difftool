package org.examples.diff.objects;

import org.examples.diff.anotations.AuditKey;

import java.util.List;

public record Vehicle(@AuditKey String key, String displayName, String brand, Long mileage)  {
}
