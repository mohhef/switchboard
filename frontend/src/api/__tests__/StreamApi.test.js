import React from 'react'
import axios from 'axios'
import Enzyme from 'enzyme'
import Adapter from 'enzyme-adapter-react-16'

Enzyme.configure({ adapter: new Adapter() });

// import StreamApi itself
import * as StreamApi from '../StreamApi'
import * as SampleData from '../SampleData'

jest.mock('axios');

const mockSingleStream = {
    streamId: 1,
    streamName: "Test 1"
}
const mockStreams = [
    mockSingleStream,
    {streamId: 2, streamName: "Test 2"}
]

const respAllStreams = {
    target: {
        streams: mockStreams
    }
}
const respSingleStream = {
    target: {
        stream: mockSingleStream
    }
}

describe('Stream Api', () => {

    afterEach(() => {
        jest.clearAllMocks();
    })

    describe('getStream', () => {
        it('should call axios.get and return a single stream\'s information', () => {
            StreamApi.getStream(123);
            
            expect(axios.get).toHaveBeenCalledWith(`http://localhost:8080/stream/123`);
        })
    })
    
    describe('getAllStreams', () => {
        it('should call axios.get and return an array of streams', () => {
            axios.get.mockResolvedValue(mockStreams);

            StreamApi.getAllStreams();

            expect(axios.get).toHaveBeenCalledWith("http://localhost:8080/stream");
        })
        it('should use sample data in the case of an error', () => {

            // TODO: fill in
        })
    })
})