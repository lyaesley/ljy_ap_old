package com.lyae.common;

import java.io.IOException;
import java.util.List;
import com.lyae.model.MatchRecord;

public interface CommonDAO {

	List<MatchRecord> jasonToObject(String fileName) throws IOException;
}
