public List<Patient> loadAll() {
    List<String[]> rows = CsvReader.read(path); // your CsvReader
    List<Patient> out = new ArrayList<>();

    for (String[] r : rows) {
        Patient p = new Patient(UUID.fromString(r[0]), r[1], r[2], r[3], r[4]);
        p.setPatient_id(UUID.fromString(r[5]));
        p.setNhs_number(Integer.parseInt(r[6]));
        // parse dates using SimpleDateFormat
        out.add(p);
    }
    return out;
}

public void append(Patient p) {
    CsvWriter.appendLine(path, toRow(p));
}

void main() {
}
