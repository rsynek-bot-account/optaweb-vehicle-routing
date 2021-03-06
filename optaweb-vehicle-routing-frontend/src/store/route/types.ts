/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Action } from 'redux';

export interface LatLng {
  readonly lat: number;
  readonly lng: number;
}

export interface LatLngWithDescription extends LatLng {
  description: string;
}

export interface Location extends LatLng {
  readonly id: number;
  // TODO decide between optional, nullable and more complex structure (displayName, fullDescription, address, ...)
  readonly description?: string;
}

export interface Route {
  readonly visits: Location[];
}

export type LatLngTuple = [number, number];

export interface RouteWithTrack extends Route {
  readonly track: LatLngTuple[];
}

export interface RoutingPlan {
  readonly distance: string;
  readonly depot: Location | null;
  // TODO visits: Location[];
  readonly routes: RouteWithTrack[];
}

export enum ActionType {
  UPDATE_ROUTING_PLAN = 'UPDATE_ROUTING_PLAN',
  DELETE_LOCATION = 'DELETE_LOCATION',
  ADD_LOCATION = 'ADD_LOCATION',
  CLEAR_SOLUTION = 'CLEAR_SOLUTION',
}

export interface AddLocationAction extends Action<ActionType.ADD_LOCATION> {
  readonly value: LatLngWithDescription;
}

export interface ClearRouteAction extends Action<ActionType.CLEAR_SOLUTION> {
}

export interface DeleteLocationAction extends Action<ActionType.DELETE_LOCATION> {
  readonly value: number;
}

export interface UpdateRouteAction extends Action<ActionType.UPDATE_ROUTING_PLAN> {
  readonly plan: RoutingPlan;
}

export type RouteAction =
  | AddLocationAction
  | DeleteLocationAction
  | UpdateRouteAction
  | ClearRouteAction;
