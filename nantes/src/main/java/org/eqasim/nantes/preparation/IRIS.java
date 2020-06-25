package org.eqasim.nantes.preparation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.matsim.api.core.v01.Coord;
import org.opengis.feature.simple.SimpleFeature;

public class IRIS {
	private final static GeometryFactory factory = new GeometryFactory();

	public final Geometry geometry;
	public final String code;

	public IRIS(Geometry geometry, String code) {
		this.geometry = geometry;
		this.code = code;
	}

	public boolean containsCoordinate(Coord coord) {
		Coordinate coordinate = new Coordinate(coord.getX(), coord.getY());
		Point point = factory.createPoint(coordinate);
		return geometry.contains(point);
	}

	public static Map<String, IRIS> read(File path) throws MalformedURLException, IOException {
		DataStore dataStore = DataStoreFinder.getDataStore(Collections.singletonMap("url", path.toURI().toURL()));
		SimpleFeatureSource featureSource = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
		SimpleFeatureCollection featureCollection = featureSource.getFeatures();
		SimpleFeatureIterator featureIterator = featureCollection.features();

		Map<String, IRIS> iris = new HashMap<>();

		while (featureIterator.hasNext()) {
			SimpleFeature feature = featureIterator.next();
			Geometry geometry = (Geometry) feature.getDefaultGeometry();
			String code = (String) feature.getAttribute("CODE_IRIS");
			iris.put(code, new IRIS(geometry, code));
		}

		featureIterator.close();
		dataStore.dispose();

		return iris;
	}
}
