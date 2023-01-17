package com.epam.redkin.model.service.impl;

import com.epam.redkin.model.entity.Station;
import com.epam.redkin.model.repository.StationRepository;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class StationServiceImplTest {

    @Test
    @Disabled("TODO: Complete this test")
    void testConstructor() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationRepository stationRepository = null;

        // Act
        StationServiceImpl actualStationServiceImpl = new StationServiceImpl(stationRepository);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#getAllStationList()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllStationList() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;

        // Act
        List<Station> actualAllStationList = stationServiceImpl.getAllStationList();

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#getStationListByCurrentPage(int, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetStationListByCurrentPage() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;
        int currentPage = 0;
        int recordsPerPage = 0;

        // Act
        List<Station> actualStationListByCurrentPage = stationServiceImpl.getStationListByCurrentPage(currentPage,
                recordsPerPage);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#getStationListSize()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetStationListSize() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;

        // Act
        int actualStationListSize = stationServiceImpl.getStationListSize();

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#updateStation(Station)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateStation() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;
        Station station = null;

        // Act
        stationServiceImpl.updateStation(station);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#getStationById(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetStationById() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;
        int stationId = 0;

        // Act
        Station actualStationById = stationServiceImpl.getStationById(stationId);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#removeStation(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRemoveStation() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;
        int stationId = 0;

        // Act
        stationServiceImpl.removeStation(stationId);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link StationServiceImpl#addStation(Station)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddStation() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of StationServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        StationServiceImpl stationServiceImpl = null;
        Station station = null;

        // Act
        stationServiceImpl.addStation(station);

        // Assert
        // TODO: Add assertions on result
    }
}

