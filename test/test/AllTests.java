/**
 * Copyright (C) 2018 Alex Kalinins
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * A test suite for all {@code JUnit} tests.
 * 
 * @author Alex Kalinins
 * @since 2018-12-30
 * @since v0.5
 * @version v1.0
 */
@RunWith(Suite.class)
@SuiteClasses({ DimensionScalerTest.class, GameCleanerTest.class, GeneratorTest.class, GenericsMathPowerTest.class,
		ImageFromArrayTest.class, KeyActionEqualsTest.class, KeyActionHashcodeTest.class })
public class AllTests {

}
