package com.lyae.common;

import java.io.IOException;
import java.util.List;
import com.lyae.model.Soccer;

public interface CommonDAO {

	List<Soccer> jasonToObject(String fileName) throws IOException;
}
